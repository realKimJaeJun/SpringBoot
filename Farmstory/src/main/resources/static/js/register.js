// 유효성 검사 정규 표현식
const reUid = /^[a-z]+[a-z0-9]{5,19}$/g;
const rePass = /^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\(\\)\-_=+]).{8,254}$/;
const reName = /^[ㄱ-힣]+$/;
const reEmail = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;
const reHp = /^01(?:0|1|[6-9])-(?:\d{4})-\d{4}$/;

window.addEventListener('load',()=>{
    // 아이디
    document.getElementById('btnCheckUid').addEventListener('click', btnCheckUidClick);

    // 비밀번호
    document.getElementById('passCheck').addEventListener('change',passCheckChange);

    // 이름
    document.querySelector('input[name=name]').addEventListener('change', NameCheck);

    // 닉네임
    document.getElementById('btnNickCheck').addEventListener('click', NickCheck);

    // 이메일
    document.getElementById('btnEmailCheck').addEventListener('click', EmailCheckClick);

    // 휴대폰 번호
    document.getElementById('hpCheck').addEventListener('change', hpCheckChange);
    document.querySelector('input[name=hp]').addEventListener('change',()=>{
    });
});

// 유효성 체크
let isUidOk = false;
let passOk = false;
let isNameOk = false;
let isNickOk = false;
let isEmailOk = false;
let isEmailAuthOk = false;
let isHpOk = false;

let isEmailCheckSend = false; // 이메일 인증코드를 보냈는가?
let emailChecking = false; // 이메일 검사중 체크
let emailCode = 0; // 이메일 인증 코드

// 아이디 중복 검사
function btnCheckUidClick() {
    if(isUidOk) {return;} // 유효성 체크

    const uid = this.previousElementSibling.value;
    const resultUid = this.nextElementSibling;

    if (uid == '') {
        alert('아이디를 입력하세요.');
        return;
    }

    if(!uid.match(reUid)) {
        isUidOk = false;
        resultUid.innerText = '영문, 숫자 포함 6자리 이상이어야 합니다.';
        resultUid.style.color = 'red';
        return;
    }

    // AJAX 전송
	const xhr = new XMLHttpRequest();
	xhr.open("GET", "/Farmstory/user/checkUid?uid="+uid);
	xhr.responseType = "json";
	xhr.send();

	xhr.onreadystatechange = function(){
		if(xhr.readyState == XMLHttpRequest.DONE){
			if(xhr.status != 200) alert("Request fail...")
			else {
				const data = xhr.response;

				if(data.result > 0){
					resultUid.innerText = '이미 사용중인 아이디 입니다.';
					resultUid.style.color = 'red';
					isUidOk = false
				} else {
					resultUid.innerText = '사용 가능한 아이디 입니다.';
					resultUid.style.color = 'green';
					isUidOk = true
				}
			}
		}
	}
}

// 비밀번호 유효성 체크
function passCheckChange() {
    const pass = document.querySelector('input[name=pass]');
    const passCheck = document.querySelector('input[name=passCheck]')
    const resultPass = document.querySelector('.resultPass');

    if(passCheck.value.match(rePass)){
        if(pass.value != passCheck.value){
            resultPass.innerText = '비밀번호가 일치 하지 않습니다.'
            resultPass.style.color = 'red';
            passOk = false
        } else {
            resultPass.innerText = '사용할수 있는 비밀번호 입니다.'
            resultPass.style.color = 'green';
            passOk = true
        }
    } else {
        passOk = false
        resultPass.innerText = '숫자, 영문, 특수문자 포함 8자리 이상 이어야 합니다.'
        resultPass.style.color = 'red';
    }
}

// 이름 유효성 체크
function NameCheck() {
	const name = this.value
	const resultName = this.nextElementSibling;

	if(name.match(reName)) {
		isNameOk = true;
		resultName.innerText = ''
	} else {
		isNameOk = false;
		resultName.innerText = '유효하지 않은 이름입니다.'
	}
}

// 닉네임 중복 검사
function NickCheck() {
	if(isNickOk) return;

	const nick = this.previousElementSibling.value;
	const resultNick = this.nextElementSibling;

	// AJAX 전송
	const xhr = new XMLHttpRequest();
	xhr.open("GET", "/Farmstory/user/checkNick?nick="+nick);
	xhr.responseType = "json";
	xhr.send();

	xhr.onreadystatechange = function(){
		if(xhr.readyState == XMLHttpRequest.DONE){
			if(xhr.status != 200) alert("Request fail...")
			else {
				const data = xhr.response;

				if(data.result > 0){
					resultNick.innerText = '이미 사용중인 닉네임 입니다.';
					resultNick.style.color = 'red';
					isNickOk = false
				} else {
					resultNick.innerText = '사용 가능한 닉네임 입니다.';
					resultNick.style.color = 'green';
					isNickOk = true
				}
			}
		}
	}
}

// 이메일 중복 체크 / 이메일 인증
function EmailCheckClick() {
    if(isEmailAuthOk) {
        alert('인증 완료');
        return;
    }

    const email = this.previousElementSibling;
    const resultEmail = this.nextElementSibling;
    const Auth = this.parentElement.lastElementChild;

    if(emailChecking){ // 이메일 체크 중일 경우 중지
        alert('...');
        return;
    } else { // 이메일 인증 시작
        emailChecking = true;
        // 이메일 검사중 이메일 수정 불가능하게 변경
        email.setAttribute('readonly', true);
    }

    resultEmail.innerText = '전송 중 입니다.';
    resultEmail.style.color = 'block';

    // 데이터 전송
    const xhr = new XMLHttpRequest();
    xhr.open("GET", "/Farmstory/user/checkEmail?email="+email.value);
    xhr.responseType = "json";
    xhr.send();

    xhr.onreadystatechange = function() {
        if(xhr.readyState == XMLHttpRequest.DONE) {
            if(xhr.status != 200) alert("Request fail...")
            else {
                const data = xhr.response;

                emailChecking = false; // 검사 진행 끝
                email.setAttribute('readonly', false); // 이메일 수정 가능

                if(data.status == 0) {
                    // 메일 발송 성공
                    emailCode = data.code;
                    isEmailCheckSend = true; // 이메일 코드 전송됨
                    resultEmail.innerText = '인증코드를 전송 했습니다. 이메일을 확인 하세요.';
                    resultEmail.style.color = 'green';
                    Auth.style.visibility = 'visible'; // 코드 입력란 보이게 변경
                } else if (data.status > 0) {
                    resultEmail.innerText = '이미 사용중인 이메일 입니다.';
                    resultEmail.style.color = 'red';
                } else {
                    resultEmail.innerText = '이메일 전송을 실패했습니다. 이메일을 확인 후 다시 시도 하시기 바랍니다.';
                    resultEmail.style.color = 'red';
                }
            }
        }
    }
}

// 휴대폰번호 중복 확인
function hpCheckChange(){
	const hp = document.querySelector('input[name=hp]').value;

	// AJAX 전송
	const xhr = new XMLHttpRequest();
	xhr.open("GET", "/Farmstory/user/checkHp?hp="+hp);
	xhr.responseType = "json";
	xhr.send();

	xhr.onreadystatechange = function(){
		if(xhr.readyState == XMLHttpRequest.DONE){
			if(xhr.status != 200) alert("Request fail...")
			else {
				const data = xhr.response;
				console.log(data);

				const resultHp = document.querySelector('.resultHp');

				if(data.result == 1){
					resultHp.innerText = '이미 사용중인 번호 입니다.';
					resultHp.style.color = 'red';
					HpOk = false
				} else {
					resultHp.innerText = '사용 가능한 번호 입니다.';
					resultHp.style.color = 'green';
					HpOk = true
				}

			}
		}
	}
}

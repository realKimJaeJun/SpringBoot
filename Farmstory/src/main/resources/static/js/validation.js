window.addEventListener('load',()=>{
    document.getElementById('btnCheckUid').addEventListener('click', btnCheckUidClick); // 아이디 중복 체크
    document.querySelector('input[name=uid]').addEventListener('change',inputUidChange); // 아이디 변경시 유효성 초기화
    document.getElementById('passCheck').addEventListener('change',passCheckChange); // 비밀번호 유효성 체크
    document.querySelector('input[name=name]').addEventListener('change', NameCheck); // 이름 유효성 체크
    document.getElementById('btnNickCheck').addEventListener('click', NickCheck); // 별병 중복 체크
    document.querySelector('input[name=nick]').addEventListener('change', NickChange); // 별명 변경시 유효성 초기화
    document.getElementById('btnEmailCheck').addEventListener('click', EmailCheckClick); // 이메일 중복 체크 클릭시 함수 실행
    document.querySelector('input[name=email]').addEventListener('change', EmailChange); // 이메일 변경시 유효성 검사
    document.getElementById('btnHpCheck').addEventListener('click', HpCheckClick); // 번호 중복 확인
    document.querySelector('input[name=hp]').addEventListener('change', HpChange); // 번호 변경시 유효성 초기화
    document.querySelector('input[class=btnRegister]').addEventListener('click', btnRegisterClick); // 최종 회원가입시 유효성 확인
    document.getElementById('btnEmailConfirm').addEventListener('click', btnEmailConfirmClick); // 이메일 인증 확인 클릭시 실행 하는 이벤트
});
// 유효성 검사 정규 표현식
const reUid = /^[a-z]+[a-z0-9]{5,19}$/g;
const rePass = /^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\(\\)\-_=+]).{8,254}$/;
const reName = /^[ㄱ-힣]+$/;
const reEmail = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;
const reHp = /^01(?:0|1|[6-9])-(?:\d{4})-\d{4}$/;

// 유효성 검사 확인
let isUidOk = false;
let passOk = false;
let isNameOk = false;
let isNickOk = false;
let isEmailOk = false; // 이메일 유효성 확인
let isEmailAuthOk = false; // 이메일 인증 확인
let isHpOk = false;

// checking
let isEmailCheckSend = false; // 이메일 인증코드 전송 유무
let emailChecking = false; // 이메일 검사중 유무
let emailCode = 0; // 이메일 인증 코드

// 이메일 인증 확인 버튼 클릭 함수
function btnEmailConfirmClick() {
    const code = this.previousElementSibling.value;
    const auth = this.parentElement;
    const emailResult = this.parentElement.previousElementSibling;

    // 입력한 코드가 이메일 코드와 맞고 이메일 코드가 전송된 상태일때
    if (code == emailCode && isEmailCheckSend == true) {
        isEmailAuthOk = true; // 이메일 인증 완료
        emailResult.innerText = '이메일이 인증 되었습니다.';
        emailResult.style.color = 'green';
        auth.style.visibility = 'hidden';
    } else {
        isEmailAuthOk = false;
        emailResult.innerText = '인증코드가 틀렸습니다.';
        emailResult.style.color = 'red';
    }
}

// 회원가입 버튼 클릭시 유효성 최종 확인
function btnRegisterClick() {
    // 폼 전송 취소
    event.preventDefault();

    // 유효성 확인
    if(!isUidOk){
        alert('아이디를 확인하여 주십시오.');
        return;
    }
    if(!passOk){
        alert('비밀번호를 확인하여 주십시오.');
        return;
    }
    if(!isNameOk){
        alert('이름을 다시 확인하여 주십시오.');
        return;
    }
    if(!isNickOk){
        alert('별명을 다시 확인하여 주십시오.');
        return;
    }
    if(!isEmailOk){
        alert('이메일을 다시 확인하여 주십시오.');
        return;
    }
    if(!isEmailAuthOk) {
        alert('이메일 인증을 해주십시오.');
        return;
    }
    if(!isHpOk){
        alert('번호를 다시 확인하여 주십시오.');
        return;
    }

    // 해당 이벤트 삭제후 폼 전송 실행
    this.removeEventListener('click', btnRegisterClick);
    this.click();

}

// 휴대폰 변경시 유효성 초기화
function HpChange() {
	isHpOk = false;
	this.parentElement.lastElementChild.innerText = '';
}

// 휴대폰 중복 확인
function HpCheckClick() {
    if(isHpOk) return;

    const hp = this.previousElementSibling.value;
    const resultHp = this.nextElementSibling;

    // 데이터 전송
    const xhr = new XMLHttpRequest();
    xhr.open("GET", "/Farmstory/user/checkHp?hp="+hp);
    xhr.responseType = "json";
    xhr.send();

    xhr.onreadystatechange = function() {
        if(xhr.readyState == XMLHttpRequest.DONE) {
            if(xhr.status != 200) alert("Request fail...")
            else {
                const data = xhr.response;

                if(data.result > 0){
                    resultHp.innerText = '이미 사용중인 번호 입니다.';
                    resultHp.style.color = 'red';
                    isHpOk = false
                } else {
                    resultHp.innerText = '사용 가능한 번호 입니다.';
                    resultHp.style.color = 'green';
                    isHpOk = true
                }
            }
        }
    }
}

// 이메일 변경시 유효성 검사
function EmailChange() {
	const email = this.value;
	const resultEmail = this.nextElementSibling.nextElementSibling;
    const Auth = this.parentElement.lastElementChild;

	isEmailAuthOk = false; // 이메일 수정시 이메일 인증 해제
	isEmailCheckSend = false; // 이메일 수정시 전송된 코드의 유효성 해제
	Auth.style.visibility = 'hidden';

	if (email.match(reEmail)) {
        isEmailOk = true;
        resultEmail.innerText = '';
    } else {
        isEmailOk = false;
        resultEmail.innerText = '유효한 이메일이 아닙니다.';
        resultEmail.style.color = 'red';
    }
}

// 이메일 중복 확인 및 이메일 메일 인증
function EmailCheckClick() {
    if(isEmailAuthOk) { //이메일 인증 완료
        alert('이미 확인이 완료 되었습니다.');
        return;
    }

    const email = this.previousElementSibling;
    const resultEmail = this.nextElementSibling;
    const Auth = this.parentElement.lastElementChild;

    if(emailChecking){ // 이메일 체크 중일 경우 중지
        alert('이메일 확인 중 입니다.');
        return;
    } else { // 이메일 인증 시작
        emailChecking = true;
        // 이메일 검사중 이메일 수정 불가능하게 변경
        email.setAttribute('readonly', true);
    }

    resultEmail.innerText = '이메일 전송 중 입니다.';
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

                if(data.status == 1) {
                    // 메일 발송 성공
                    emailCode = data.code;
                    isEmailCheckSend = true; // 이메일 코드 전송됨
                    resultEmail.innerText = '인증코드를 전송 했습니다. 이메일을 확인 하세요.';
                    resultEmail.style.color = 'green';
                    Auth.style.visibility = 'visible'; // 코드 입력란 보이게 변경
                } else if (data.status == -1) {
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

// 별명 변경시 유효성 취소 함수
function NickChange() {
	isNickOk = false;
	this.parentElement.lastElementChild.innerText = '';
}

// 별명 중복 체크
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

// 이름 유효성 체크
function NameCheck() {
	const name = this.value
	const resultName = this.nextElementSibling;

	if(name.match(reName)) {
		isNameOk = true;
		resultName.innerText = ''
	} else {
		isNameOk = false;
		resultName.innerText = '올바른 이름을 입력하세요.'
	}

}

// 비밀번호 유효성 검사 체크 함수
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
        resultPass.innerText = '비멀번호는 숫자,영문,특수문자 포함 8자리 이상 이어야 합니다.'
        resultPass.style.color = 'red';
    }
}

// 아이디 변경시 유효성 초기화
function inputUidChange() {
    isUidOk = false;
    this.nextElementSibling.nextElementSibling.innerText = '';
}

// 아이디 중복 체크 함수
function btnCheckUidClick() {
    if(isUidOk) {return;} // 유효성 검사 확인 된 경우 리턴

    const uid = this.previousElementSibling.value;
    const resultUid = this.nextElementSibling;

    if (uid == '') {
        alert('아이디를 입력하세요.');
        return;
    }

    if(!uid.match(reUid)) {
        isUidOk = false;
        resultUid.innerText = '6자리 이상 숫자 영문을 포함한 아이디여야 합니다.';
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
					resultUid.innerText = '이미 사용중인 닉네임 입니다.';
					resultUid.style.color = 'red';
					isUidOk = false
				} else {
					resultUid.innerText = '사용 가능한 닉네임 입니다.';
					resultUid.style.color = 'green';
					isUidOk = true
				}
			
			}
		}
	}

}
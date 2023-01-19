/**
 * register 유효성 검사
 */
// 정규표현식
const reUid = /^[a-z]+[a-z0-9]{5,19}$/g;
const rePass = /^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\(\\)\-_=+]).{8,254}$/;
const reName = /^[ㄱ-힣]+$/;
const reEmail = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;
const reHp = /^01(?:0|1|[6-9])-(?:\d{4})-\d{4}$/;


 window.addEventListener('load', ()=>{
	// 아이디
	document.getElementById('btnCheckUid').addEventListener('click', IdCheck);
	document.querySelector('input[name=uid]').addEventListener('change',()=>{
		
	});
	
	// 닉네임
	document.getElementById('btnCheckNick').addEventListener('click', NickCheck);
	document.querySelector('input[name=nick]').addEventListener('change',()=>{
		
	});
	
	// 휴대폰번호
	document.getElementById('CheckHp').addEventListener('change', HpCheck);
	document.querySelector('input[name=hp]').addEventListener('change',()=>{
		
	});
	
	// 비밀번호
	document.getElementById('passCheck').addEventListener('change', passwordCheck);
});

// 유효성 체크
let IdOk = false
let NickOk = false
let HpOk = false
let passOk = false

// 아이디 중복 확인
function IdCheck(){
	const uid = document.querySelector('input[name=uid]').value;
	
	// AJAX 전송
	const xhr = new XMLHttpRequest();
	xhr.open("GET", "/Sboard/user/checkUid?uid="+uid);
	xhr.responseType = "json";
	xhr.send();
	
	xhr.onreadystatechange = function(){
		if(xhr.readyState == XMLHttpRequest.DONE){
			if(xhr.status != 200) alert("Request fail...")
			else {
				const data = xhr.response;
				console.log(data);
				
				const resultUid = document.querySelector('.resultUid');
				
				if(data.result == 1){
					resultUid.innerText = '이미 사용중인 아이디 입니다.';
					resultUid.style.color = 'red';
					IdOk = false
				} else {
					resultUid.innerText = '사용 가능한 아이디 입니다.';
					resultUid.style.color = 'green';
					IdOk = true
				}
			
			}
		}
	}
};

// 닉네임 중복 확인
function NickCheck(){
	const nick = document.querySelector('input[name=nick]').value;
	
	// AJAX 전송
	const xhr = new XMLHttpRequest();
	xhr.open("GET", "/Sboard/user/checkNick?nick="+nick);
	xhr.responseType = "json";
	xhr.send();
	
	xhr.onreadystatechange = function(){
		if(xhr.readyState == XMLHttpRequest.DONE){
			if(xhr.status != 200) alert("Request fail...")
			else {
				const data = xhr.response;
				console.log(data);
				
				const resultNick = document.querySelector('.resultNick');
				
				if(data.result == 1){
					resultNick.innerText = '이미 사용중인 닉네임 입니다.';
					resultNick.style.color = 'red';
					NickOk = false
				} else {
					resultNick.innerText = '사용 가능한 닉네임 입니다.';
					resultNick.style.color = 'green';
					NickOk = true
				}
			
			}
		}
	}
};

// 휴대폰번호 중복 확인
function HpCheck(){
	const hp = document.querySelector('input[name=hp]').value;
	
	// AJAX 전송
	const xhr = new XMLHttpRequest();
	xhr.open("GET", "/Sboard/user/checkHp?hp="+hp);
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
};

// 비밀번호 확인 체크 / 비밀번호 중복 체크 / 정규표현식 유효성 검사
function passwordCheck() {
	const pass = document.querySelector('input[name=pass]');
	const passCheck = document.querySelector('input[name=passCheck]')
	const resultPass = document.querySelector('.resultPass');
	
	if(passCheck.value.match(rePass)){
		if(pass.value != passCheck.value){
			resultPass.innerText = '비밀번호가 일치 하지 않습니다.'
			passOk = false
		} else {
			resultPass.innerText = '사용할수 있는 비밀번호 입니다.'
			passOk = true
		}
	} else {
		passOk = false
		resultPass.innerText = '비멀번호는 숫자,영문,특수문자 포함 8자리 이상 이어야 합니다.'
	}
}
window.addEventListener('load',()=>{
    document.getElementById('btnNext').addEventListener('click',btnNextClick);
});

function btnNextClick() {
    event.preventDefault();
    if(document.querySelector('input[class=terms]').checked && document.querySelector('input[class=privacy]').checked) {
        alert('동의 하셨습니다.');
        location.href='/Farmstory/user/register';
    } else {
        alert('동의란에 체크 되어있지 않습니다..');
    }
}
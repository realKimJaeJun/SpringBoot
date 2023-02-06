/*
window.addEventListener('load',()=>{
    nullCheck(document.getElementById('schoolTable'));

    document.getElementById('growTable').addEventListener('load', nullCheck());
    document.getElementById('schoolTable').addEventListener('load', nullCheck());
    document.getElementById('storyTable').addEventListener('load', nullCheck());
});
*/

function nullCheck() {
    console.log(1);
    console.log(this);

    if(this.children.length == 0) {
        const body = document.createElement('tbody');
        this.appendChild(body);
    }

    const tbody = this.children[0];

    if(tbody.children.length < 5) {
        console.log(2);
        for(let i = 0; i < 6 - tbody.children.length; i++) {
            console.log(3);
            let tr = document.createElement('tr');

            let td = document.createElement('td');
            let a = document.createElement('a');
            a.innerHTML = '등록된 게시물이 없습니다.'

            td.appendChild(a);
            tr.appendChild(td);

            tbody.appendChild(tr);
        }
    }
}
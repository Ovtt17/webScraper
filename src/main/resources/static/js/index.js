const searchEvent = document.querySelector('.btn-search');
searchEvent.addEventListener('click', search);


function search() {
    let textToSearch = document.getElementById('input-search').value;
    document.location.href = 'results.html?query=' + textToSearch;
}
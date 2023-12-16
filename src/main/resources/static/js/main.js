let aux = document.location.href.split('?query=');
let query = aux[1];

document.getElementById("input-search").value = query;

fetch('http://localhost:8080/api/search?query=' + query)
.then(response => response.json())
.then(json => {
    console.log(json);

    let html = '';
    for (let resultSearch of json) {
        html += getHTMLResultSearch(resultSearch);
    }
    document.getElementById('links').outerHTML = html;
})

function getHTMLResultSearch(resultSearch) {
    return `<div class="result">
                <h3><a href="${resultSearch.url}" target="_blank">${resultSearch.title}</a></h3>
                <span>${resultSearch.description}</span>
            </div>`;
}
const filterButton = document.querySelector("#btn-filter");
filterButton.addEventListener('click', makeRequest)

function filterTable () {
    const url = '/ads';
    $('#result-table').load(url)
}

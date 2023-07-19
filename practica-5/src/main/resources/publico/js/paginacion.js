importScripts('/js/axios.min.js'); // JQuery trabaja con el DOM no puede ser utilizada
function fetchArticles(pageNumber) {
    axios.get(`/articles?page=${pageNumber}`)
        .then(function (response) {
            const articles = response.data.articles;
            const previousPage = response.data.previousPage;
            const nextPage = response.data.nextPage;

            const articleList = document.getElementById('article-list');
            articleList.innerHTML = '';

            articles.forEach(function (article) {
                const articleDiv = document.createElement('div');
                articleDiv.innerHTML = `
                    <h2>${article.title}</h2>
                    <p>${article.content}</p>
                `;
                articleList.appendChild(articleDiv);
            });

            const previousBtn = document.getElementById('previous-btn');
            const nextBtn = document.getElementById('next-btn');

            if (previousPage) {
                previousBtn.disabled = false;
                previousBtn.onclick = function () {
                    fetchArticles(previousPage);
                };
            } else {
                previousBtn.disabled = true;
            }

            if (nextPage) {
                nextBtn.disabled = false;
                nextBtn.onclick = function () {
                    fetchArticles(nextPage);
                };
            } else {
                nextBtn.disabled = true;
            }
        })
        .catch(function (error) {
            console.log("Error:", error);
        });
}


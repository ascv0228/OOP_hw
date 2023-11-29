
function createGridItem(bookToken, bookData) {
    console.log(bookData)
    const { title, description, language, genres } = bookData["bookInfo"];
    const gridItem = document.createElement('div');
    gridItem.classList.add('grid-item');
    gridItem.innerHTML = `<strong>Title:</strong> ${title}<br>` +
        `<strong>Description:</strong> ${description}<br>` +
        `<strong>Language:</strong> ${language ? language : "Unknown"}<br>` +
        `<strong>Genres:</strong> ${genres ? genres : "Unknown"}<br>`
        ;
    gridItem.id = bookToken;

    gridItem.addEventListener('click', () => {
        window.location.href = `/seeBook?bookToken=${bookToken}`
    });

    return gridItem;
}

function handleSendRequest() {
    return new Promise(function (resolve, reject) {
        // Make an AJAX request (GET in this case) to the server
        $.ajax({
            type: "GET",
            url: '/bookCollect/api',
            success: function (response) {
                resolve(response); // Resolve the promise with the response
            },
            error: function (error) {
                reject(error); // Reject the promise with the error
            }
        });
    });
}

function refresh() {
    // Usage
    handleSendRequest().then(function (response) {
        const bookData = JSON.parse(response);

        const PBookgridContainer = document.getElementById('pbookGrid');
        const EBookgridContainer = document.getElementById('ebookGrid');

        for (const bookToken in bookData) {
            if (bookData.hasOwnProperty(bookToken)) {

                const gridItem = createGridItem(bookToken, bookData[bookToken]);
                if (bookData[bookToken].bookForm == "pbook") {
                    PBookgridContainer.appendChild(gridItem);
                }
                else if (bookData[bookToken].bookForm == "ebook") {
                    EBookgridContainer.appendChild(gridItem);
                }

            }
        }
    }).catch(function (error) {
        console.error("Error:", error)
    });

}
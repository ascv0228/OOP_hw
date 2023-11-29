// function handleSubmit() {
//     const array1 = ["bookToken"];
//     if (!array1.every(checkText)) {
//         alert("Please check input");
//         return;
//     }

//     var inputValue = outputInputValue(array1);

//     // Make an AJAX request (POST or GET) to the server
//     $.ajax({
//         type: "GET", // or "GET" depending on your needs
//         // url: '/hello?name=' + inputValue,
//         url: productUrl('/seeBook/api?', inputValue),
//         success: function (response) {
//             // Update the text box with the response
//             $("#responseTextBox").val(response);
//         },
//         error: function (error) {
//             console.error("Error:", error);
//         }
//     });
// }

// function handleOperation() {
//     var bookTokenBox = document.getElementById("bookToken");
//     if (bookTokenBox) {
//         window.location.href = `/operation?bookToken=${bookTokenBox.value}`

//     } 
// }

window.onload = function () {
    handleSendRequest("/seeMember/api").then(function (response) {
        const MemberGrid = document.getElementById('myForm');

        memberJsonToHtml(MemberGrid, response);

    }).catch(function (error) {
        console.error("Error:", error)
    });
};

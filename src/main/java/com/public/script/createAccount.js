function handleSubmit() {
    const array1 = ["name", "authority", "gender"];
    if (!array1.every(checkText)) {
        alert("Please check input");
        return;
    }

    var inputValue = outputInputValue(array1);
    console.log(inputValue)

    $.ajax({
        type: "GET",
        url: '/createAccount/api?' + inputValue,
        success: function (response) {
            // $("#responseTextBox").val(response);
            const MemberGrid = document.getElementById('MemberGrid');

            MemberGrid.innerHTML = '';
            memberJsonToHtml(MemberGrid, response);
        },
        error: function (error) {
            console.error("Error:", error);
        }
    });
}
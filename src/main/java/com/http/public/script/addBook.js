
function handleSubmit() {
    const array1 = ["title", "description", "bookForm", "language", "location"];
    if (!array1.every(checkText)) {
        alert("Please check input");
        return;
    }
    var selectedGenres = get_genreValue()
    if (selectedGenres.length < 1) {
        alert("Please check genre");
        return;
    }

    var inputValue = outputInputValue(array1) + "&bookGenres=" + selectedGenres;
    console.log(inputValue)

    // Make an AJAX request (POST or GET) to the server
    $.ajax({
        type: "GET",
        url: productUrl('/addBook/api?', inputValue),
        success: function (response) {
            $("#responseTextBox").val(response);
        },
        error: function (error) {
            $("#responseTextBox").val("Error:", error);
        }
    });
}

function get_genreValue() {
    var selectedGenres = [];
    $("input[type=checkbox]:checked").each(function () {
        selectedGenres.push($(this).val());
    });
    return selectedGenres.join("+");
}
function showHideLocation() {
    var select_bookForm = document.getElementById("bookForm");
    var box_location = document.getElementById("location");
    var box_location_label = document.querySelector('label[for="location"]');

    // Get the selected value from selectA
    var select_bookForm_value = select_bookForm.value;

    // Show or hide selectB based on the selected value
    if (select_bookForm_value === "pbook") {
        box_location.classList.remove("hidden");
        box_location_label.classList.remove("hidden");
    } else {
        box_location.classList.add("hidden");
        box_location_label.classList.add("hidden");
        // Optionally reset the value of selectB
        box_location.value = "-";
    }
} 
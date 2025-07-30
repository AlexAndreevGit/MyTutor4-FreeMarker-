// ChatBotAPI_6

function performSearch() {

    console.log("Test1")
    const question = document.getElementById('searchInput').value.trim();

    if (question === '') {
        alert('Please enter a question');
        return;
    }

    console.log("Test2")

    // Show loading indicator
    const resultsOuterContainerDiv = document.getElementById('resultsOuterContainer');
    resultsOuterContainerDiv.style.display = 'block';

    console.log("Test3")

    const resultsDiv = document.getElementById('searchResults');
    resultsDiv.innerHTML = '<p>Thinking about your question...</p>';

    console.log("Test4")

    // Make AJAX request
    fetch('/tutorials/ask-question', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'X-CSRF-TOKEN': document.querySelector('meta[name="_csrf"]').getAttribute('content')
        },
        body: JSON.stringify({ query: question })
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            console.log("Test5",data)
            displayResults(data);
        })
        .catch(error => {
            resultsDiv.innerHTML = '<p class="text-danger">Error: ' + error.message + '</p>';
        });
}

function displayResults(data) {

    console.log("Test6",data)

    const resultsDiv = document.getElementById('searchResults');
    console.log("resultsDiv",resultsDiv)
    resultsDiv.innerHTML = marked.parse(data["answer"]);
}

// Add event listener for the button click
document.getElementById('searchButton').addEventListener('click', performSearch);

// Add event listener for the Enter key press in the input field
document.getElementById('searchInput').addEventListener('keypress', function(event) {

    // Check if the key pressed was "Enter"
    if (event.key === 'Enter') {
        // Prevent the default form submission behavior
        event.preventDefault();
        // Call the same function as the button click
        performSearch();
    }
});
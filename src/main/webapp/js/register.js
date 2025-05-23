document.getElementById('registerForm').addEventListener('submit', function(e) {
    e.preventDefault();

    const data = {
        name: e.target.name.value,
        email: e.target.email.value,
        password: e.target.password.value
    };

    fetch('/register', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
    .then(res => res.json())
    .then(result => alert(result.message))
    .catch(err => console.error('Error:', err));
});

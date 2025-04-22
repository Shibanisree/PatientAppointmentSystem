document.addEventListener('DOMContentLoaded', () => {
    const urlParams = new URLSearchParams(window.location.search);
    if (urlParams.get('error')) {
        document.getElementById('error').style.display = 'block';
    }
});
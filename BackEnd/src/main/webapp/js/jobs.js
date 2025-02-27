document.querySelector('.job-search button').addEventListener('click', () => {
    const keyword = document.querySelector('.job-search input').value.toLowerCase();
    const jobs = document.querySelectorAll('.job-posting');

    jobs.forEach(job => {
        const title = job.querySelector('h3').textContent.toLowerCase();
        const description = job.querySelector('p').textContent.toLowerCase();

        if (title.includes(keyword) || description.includes(keyword)) {
            job.style.display = 'block';
        } else {
            job.style.display = 'none';
        }
    });
});

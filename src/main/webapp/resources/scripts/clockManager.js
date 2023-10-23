const hour = document.querySelector('.hour');
const minute = document.querySelector('.minute');
const second = document.querySelector('.second');
const dateField = document.querySelector('.date-window')
setInterval(() => {
    const d = new Date(); //object of date()
    const hr = d.getHours();
    const min = d.getMinutes();
    const sec = d.getSeconds();
    const day = d.getDate();
    const hr_rotation = (30) * hr + min / (4); //converting current time
    const min_rotation =(6) * min;
    const sec_rotation = 6 * sec;

    hour.style.transform = `rotate(${hr_rotation}deg)`;
    minute.style.transform = `rotate(${min_rotation}deg)`;
    second.style.transform = `rotate(${sec_rotation}deg)`;

    dateField.textContent = day.toString();
}, 8000);
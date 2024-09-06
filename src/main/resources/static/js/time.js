/**
 * Sets the background image and text color of the page based on the current time of day.
 * If the current hour is between 6 PM and 6 AM, the background is set to a night image and the text color is set to white.
 * Otherwise, the background is set to a sunny image and the text color is set to black.
 */
function setBackgroundBasedOnTime() {
    const currentHour = new Date().getHours();
    const isNight = currentHour >= 18 || currentHour < 6;
    const backgroundImage = isNight ? 'night.jpg' : 'sunny.jpg';
    document.body.style.backgroundImage = `url(${backgroundImage})`;
    document.body.style.backgroundSize = 'cover';
    document.body.style.backgroundRepeat = 'no-repeat';
    document.body.style.backgroundAttachment = 'fixed';
    const textColor = isNight ? 'white' : 'black';
    const headings = document.querySelectorAll('h1, h2');
    headings.forEach(heading => {
        heading.style.color = textColor;
    });
}    
setBackgroundBasedOnTime();
setInterval(setBackgroundBasedOnTime, 60000); // Update every minute
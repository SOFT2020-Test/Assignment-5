const logger = require("./logger")
const webdriver = require('selenium-webdriver'),
    By = webdriver.By,
    until = webdriver.until;

const driver = new webdriver.Builder()
    .forBrowser('chrome')
    .build();

driver.get('https://www.youtube.com/results?search_query=cat+videos&sp=EgIIAg%253D%253D')
.then(async () => {
    await driver.findElements(webdriver.By.xpath('//*[@id="video-title"]'))
        .then(res => {
            var links = res.map(aTags => aTags.getAttribute("href"))
            Promise.all(links).then(video => {
                console.log("\n===FINDING YOUTUBE CAT VIDEOS ===")
                var x = [];
                for(var i = 0; i < 10; i++) {
                    logger.info("[Link] - " + video[i])
                }
            });
        })
        .catch(err => {console.log('err: ', err);
        });
    await driver.sleep(10000).then(() => {
        driver.quit();
    })
});

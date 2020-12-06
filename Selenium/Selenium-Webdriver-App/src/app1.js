const webdriver = require('selenium-webdriver'),
    By = webdriver.By,
    until = webdriver.until;

const driver = new webdriver.Builder()
    .forBrowser('chrome')
    .build();

    
driver.get('https://sqengineer.com/practice-sites/practice-tables-selenium/')
.then(async () => {

    await driver.findElements(webdriver.By.xpath('//*[@id="table1"]//a'))
        .then(res => {
            var links = res.map(aTags => aTags.getText()) // .getAttribute("href")
            Promise.all(links).then(texts => {                
                console.log("\n===FINDING ALL LINKS FROM TABLE 1 ===")
                var x = [];
                texts.forEach(text => x.push(text))
                console.table(x)
            });
        })
        .catch(err => {console.log('err: ', err);
        });
    await driver.findElement(webdriver.By.linkText("Test Complete")).click()
        .then(() => {
            console.log("\n===CLICKING LAST LINK ===")
            console.log("Clicked the last link and were redirected :)")
        }).catch(err => {console.log("err: " + err)})

    await driver.sleep(10000).then(() => {
        driver.quit();
    })
});

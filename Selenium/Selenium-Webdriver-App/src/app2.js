
const webdriver = require('selenium-webdriver'),
By = webdriver.By,
until = webdriver.until;

const driver = new webdriver.Builder()
.forBrowser('chrome')
.build();


driver.get('https://sqengineer.com/practice-sites/practice-tables-selenium/')
.then(async () => {
    console.log("=== PRINTING INFO FORM TABLE 2 ===")
    const tableTennis = await driver.findElement(webdriver.By.xpath('//*[@id="table2"]/tbody/tr[1]/td[1]')).getText();
    const badminton = await driver.findElement(webdriver.By.xpath('//*[@id="table2"]/tbody/tr[3]//td')).getText();
    const cricket = await driver.findElement(webdriver.By.xpath('//*[@id="table2"]/tbody/tr[1]//td[3]')).getText();
    const volley = await driver.findElement(webdriver.By.xpath('//*[@id="table2"]/tbody/tr[5]//td[3]')).getText();
    console.table({tableTennis, badminton, cricket, volley})
    
    await driver.sleep(5000).then(() => {
        driver.quit();
    })
});
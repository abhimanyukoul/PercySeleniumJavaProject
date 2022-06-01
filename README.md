# PercySeleniumJavaProject
Percy Selenium Java Project on BrowserStack

Please follow the below steps to execute your (Maven based) java selenium scripts on the BrowserStack Automate Platform -

**STEP 1: Take pull of this repository into your local folder**

git clone https://github.com/abhimanyukoul/PercySeleniumJavaProject.git

**STEP 2: Open the cloned project in IDE (Eclipse, IntelliJ) of your choice**

**STEP 3: Open a (New) Terminal Session from within your IDE. The Terminal Session should be pointing to the Project Directory.**

On executing ls -l command on your terminal you should see the following folders -

a. package-lock.json

b. pom.xml

c. src

d. target

**STEP 4: Run 'npm install @percy/cli' on the terminal**

This will install all the required node modules (required to run Percy Tests on the BrowserStack Platform) in your project directory. On executing ls -l command on your terminal you should see the following folders -

a. node_modules (Should include @percy module/folder)

b. package-lock.json

c. pom.xml

d. src

e. target

**STEP 5: Run 'npx percy exec -- mvn test -Dtest=myFirstPercySeleniumScriptTest' to execute your Percy test on the [BrowserStack Automate Platform](https://automate.browserstack.com/dashboard/v2)**

Tests that can be used -

a. myFirstPercySeleniumScriptTest - To perform percy test of a publicly hosted website

b. mySecondPercySeleniumScriptLocalTest - To perform percy test of website behind a VPN (or corporate firewall) or hosted on internal networks (--force-local parameter is set as true)

**Reference Docs: https://docs.percy.io/docs/java-selenium**

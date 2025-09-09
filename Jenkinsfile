pipeline {
   agent any
   
   environment {
      BRANCH_NAME = 'master'
      ECLIPSE_WORKSPACE = 'C:\\Users\\anike\\eclipse-workspace\\SauceDemo'
      COMMIT_MESSAGE = 'Jenkins: Auto-commit after build'
   }
   
   // Auto-trigger every 5 mins on Git changes
   triggers {
      pollSCM('H H * * *')
   }
   
   stages {
      stage('Checkout from Git') {
         steps {
            git branch: "${env.BRANCH_NAME}",
            url: 'https://github.com/adityakardile6329/SauceDemo_CapstoneProject'
         }
      }
      
      stage('Copy Files from Eclipse Workspace') {
         steps {
            bat """
            echo Copying files from Eclipse workspace...
            xcopy /E /Y /I "${ECLIPSE_WORKSPACE}\\*" "."
            """
         }
      }
      
      stage('Build & Test') {
         steps {
            bat 'mvn clean test -DsuiteXmlFile=src/test/resources/testng.xml'
         }
      }
      
      stage('Commit & Push Changes') {
         steps {
            script {
               echo 'Checking for changes to push...'
               withCredentials([usernamePassword(
               credentialsId: '36821c07-230e-4b74-b3d2-a8907a056d23',
               usernameVariable: 'GIT_USER',
               passwordVariable: 'GIT_TOKEN')]) {
                  
                  bat """
                  git config user.email "adityakardile31@gmail.com"
                  git config user.name "Aditya"
                  
                  git status
                  git add .
                  
                  REM Commit only if there are changes
                  git diff --cached --quiet || git commit -m "${COMMIT_MESSAGE}"
                  
                  REM Push using token
                  git push https://%GIT_USER%:%GIT_TOKEN%@github.com/adityakardile6329/SauceDemo_CapstoneProject.git ${BRANCH_NAME}
                  """
               }
            }
         }
      }
   }
   
   post {
      always {
         // Archive screenshots
         archiveArtifacts artifacts: 'screenshots/*', fingerprint: true
         
         // Publish Cucumber Report
         publishHTML(target: [
         allowMissing: false,
         alwaysLinkToLastBuild: true,
         keepAll: true,
         reportDir: 'reports/cucumber-reports',
         reportFiles: 'cucumber-report.html',
         reportName: 'Cucumber Report'
         ])
         
         // Publish Extent Report
         publishHTML(target: [
         allowMissing: false,
         alwaysLinkToLastBuild: true,
         keepAll: true,
         reportDir: 'reports/extent-reports',
         reportFiles: 'index.html',
         reportName: 'Extent Report'
         ])
      }
   }

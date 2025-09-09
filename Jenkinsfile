pipeline {
    agent any
 
    environment {
        GIT_CREDENTIALS_ID = '36821c07-230e-4b74-b3d2-a8907a056d23' // Replace with your actual credentials ID
        BRANCH_NAME = 'master' // Set your target branch
        ECLIPSE_WORKSPACE = 'C:\\Users\\anike\\eclipse-workspace\\SauceDemo' // Update to your actual path
        COMMIT_MESSAGE = 'Automated commit from Jenkins'
    }
 
    stages {
        stage('Clean Workspace') {
            steps {
                cleanWs()
            }
        }
 
        stage('Checkout from Git') {
            steps {
                checkout([$class: 'GitSCM',
                    branches: [[name: "*/${env.BRANCH_NAME}"]],
                    userRemoteConfigs: [[
                        url: 'https://github.com/adityakardile6329/SauceDemo_CapstoneProject', // Replace with your repo URL
                        credentialsId: "${env.GIT_CREDENTIALS_ID}"
                    ]]
                ])
            }
        }
 
        stage('Copy Files from Eclipse Workspace') {
            steps {
                bat """
                echo Copying files from Eclipse workspace...
                robocopy "${ECLIPSE_WORKSPACE}" "." /E /XD ".git"
                """
            }
        }
 
        stage('Configure Git') {
            steps {
                bat """
                git config user.email "adityakardile31@gmail.com"
                git config user.name "Aditya Kardile"
                """
            }
        }
 
        stage('Check Git Status') {
            steps {
                bat 'git status'
            }
        }
 
        stage('Commit & Push Changes') {
            steps {
                bat """
                git add .
 
                REM Check if there are changes to commit
                git diff --cached --quiet
                IF %ERRORLEVEL% NEQ 0 (
                    echo Changes detected, committing...
                    git commit -m "${COMMIT_MESSAGE}"
 
                    echo Pulling latest changes from remote...
                    git pull origin ${BRANCH_NAME} --rebase
 
                    echo Pushing changes to remote...
                    git push origin ${BRANCH_NAME}
                ) ELSE (
                    echo No changes to commit.
                )
                """
            }
        }
    }
 
    post {
        success {
            echo 'Push to Git completed (if there were changes).'
        }
        failure {
            echo 'Build failed. Check console output.'
        }
    }
}
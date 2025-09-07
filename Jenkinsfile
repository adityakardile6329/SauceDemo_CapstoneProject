pipeline {
   agent any

   environment {
       APP_ENV = 'dev'
   }

   stages {
       stage('Clone') {
           steps {
               git 'https://github.com/adityakardile6329/SauceDemo_CapstoneProject'
           }
       }

       stage('Build') {
           steps {
               echo 'Building the project...'
               bat 'mvn clean install'
           }
       }

       stage('Test') {
           steps {
               echo 'Running tests...'
               bat 'mvn test'
           }
       }

       stage('Deploy') {
           steps {
               echo "Deploying to ${env.APP_ENV} environment..."
              
           }
       }
   }

   post {
       always {
           
           junit '**/target/surefire-reports/*.xml'

          
          
       }
       success {
           echo 'Pipeline completed successfully.'
       }
       failure {
           echo 'Pipeline failed.'
       }
   }
}

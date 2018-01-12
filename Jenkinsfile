pipeline {
  agent any

  stages {

    stage('Build') {
      steps {
        sh "${tool 'sbt-0.13.15'}/bin/sbt -no-colors compile test:compile"
      }
    }

    stage('Test') {
      steps {
        sh "${tool 'sbt-0.13.15'}/bin/sbt -no-colors test"
        junit '**/target/test-reports/*.xml'
      }
    }

  }

  post {
    success {
      echo "Successfully build."
    }
    failure {
      echo "Unknown build failure."
    }
  }

}

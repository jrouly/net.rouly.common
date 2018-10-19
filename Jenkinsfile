pipeline {
  agent any

  stages {

    stage('Build') {
      steps {
        sh 'echo "hello world"'
      }
    }

    stage('Test') {
      steps {
        sh 'echo "hello world"'
      }
    }

  }

  post {
    success {
      echo "Build successful."
    }

    failure {
      echo "Build failure."
    }
  }

}

def sbt(params) {
  return "sbt -Dsbt.global.base=.sbt -Dsbt.boot.directory=.sbt -Dsbt.ivy.home=.ivy2 $params"
}

pipeline {
  agent any

  options {
    timeout(time: 10, unit: 'MINUTES')
    ansiColor('xterm')
  }

  stages {

    stage('Build and Test') {
      agent { docker { image 'jrouly/sbt:0.13.15' } }
      steps {
        sh sbt('compile test')
      }
    }

    stage('Publish') {
      when { branch 'master' }
      agent { docker { image 'jrouly/sbt:0.13.15' } }
      steps {
        sh sbt('publish')
      }
    }

  }

  post {
    success {
      script {
        currentBuild.description = "Success"
      }
    }
  }

}

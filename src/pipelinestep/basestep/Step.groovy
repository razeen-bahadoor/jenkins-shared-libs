package pipelinestep.basestep
// please see https://www.jenkins.io/doc/book/pipeline/shared-libraries/ Accessing steps section
class Step {

    def steps

    Step(steps) {
        this.steps = steps
    }

}
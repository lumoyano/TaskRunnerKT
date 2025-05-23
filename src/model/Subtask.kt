package model


// collection of steps
class Subtask(val title: String) {
    private val steps = mutableListOf<Step>()

    fun addStep(step: Step) {
        steps.add(step)
    }

    // get a collections of tools from each individual step
    fun getTools(): Set<String> {
        return steps.mapNotNull { it.tool.takeIf { t -> t.isNotBlank() } }.toSet()
    }

    fun toFormattedString(): String {
        val sb = StringBuilder()
        sb.appendLine("Subtask: $title")

        //display a summary of all tools
        val tools = getTools()
        if (tools.isNotEmpty()) {
            sb.appendLine("Tools: ${tools.joinToString(", ")}")
        }

        //append steps
        for ((i, step) in steps.withIndex()) {
            sb.append("${i + 1}. ${step.instruction}")
            if (step.tool.isNotBlank()) sb.append(" [tool: ${step.tool}]")
            sb.appendLine()
            if (step.tip.isNotBlank()) sb.appendLine("    Tip: ${step.tip}")
        }

        return sb.toString()
    }
}

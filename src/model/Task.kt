package model


//collection of subtasks
class Task(val title: String) {
    private val subtasks = mutableListOf<Subtask>()

    fun addSubtask(subtask: Subtask) {
        subtasks.add(subtask)
    }

    fun getSubtasks(): List<Subtask> = subtasks

    fun generateChecklist(): String {
        val sb = StringBuilder()
        sb.appendLine("=== Task: $title ===")

        for (subtask in subtasks) {
            sb.appendLine()
            sb.appendLine(subtask.toFormattedString())
        }

        return sb.toString()
    }
}

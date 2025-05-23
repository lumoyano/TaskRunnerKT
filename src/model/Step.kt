package model


// single line inside a subtask
open class Step(
        val instruction: String,
        val tool: String,
        val tip: String
) {
    override fun toString(): String {
        return "- $instruction\n  Tool: $tool\n  Tip: $tip"
    }
}

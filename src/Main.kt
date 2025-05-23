import model.Task
import model.Subtask
import model.Step

fun main() {
    val tasks = mutableMapOf<String, Task>()
    println("Welcome to the Kotlin Task Checklist Manager")


    // this whole block an example to display
    val sampleTask = Task("Blower Motor Replacement")

    val subtask1 = Subtask("Remove battery (for safety)")
    subtask1.addStep(Step("Pop the hood", tool = "N/A", tip = "Let the engine bay cool down first if recently driven."))
    subtask1.addStep(Step("Remove terminal bolts", tool = "10mm", tip = "Always remove the negative cable first to avoid shorting."))
    subtask1.addStep(Step("Remove battery bracket", tool = "13mm", tip = "N/A"))
    subtask1.addStep(Step("Lift battery out carefully",  tool = "N/A",tip = "Batteries are heavy — lift with legs, not back."))

    val subtask2 = Subtask("Access blower motor behind glove box")
    subtask2.addStep(Step("Remove glove box screws", tool = "Phillips screwdriver", tip = "N/A"))
    subtask2.addStep(Step("Lower glove box to access interior panel", tool = "N/A", tip = "N/A"))
    subtask2.addStep(Step("Remove interior trim clips", tool = "Trim removal tool", tip = "N/A"))
    subtask2.addStep(Step("Disconnect blower motor wiring harness", tool = "N/A", tip = "Use a pick tool if the connector is stuck."))

    val subtask3 = Subtask("Replace blower motor")
    subtask3.addStep(Step("Remove blower motor mounting screws", tool = "8mm socket", tip = "N/A"))
    subtask3.addStep(Step("Pull out old blower motor", tool = "N/A", tip = "N/A"))
    subtask3.addStep(Step("Transfer fan blade to new motor (if separate)", tool = "Torx bit", tip = "Mark orientation of fan blades before removal."))
    subtask3.addStep(Step("Install new motor and tighten screws", tool = "N/A", tip = "N/A"))
    subtask3.addStep(Step("Reconnect wiring harness", tool = "N/A", tip = "N/A"))

    val subtask4 = Subtask("Reassemble and test")
    subtask4.addStep(Step("Reinstall interior trim and glove box", tool = "N/A", tip = "N/A"))
    subtask4.addStep(Step("Reinstall battery and tighten terminals", tool = "10mm", tip = "Connect positive terminal first this time."))
    subtask4.addStep(Step("Turn ignition to test blower motor function", tool = "N/A", tip = "N/A"))

    sampleTask.addSubtask(subtask1)
    sampleTask.addSubtask(subtask2)
    sampleTask.addSubtask(subtask3)
    sampleTask.addSubtask(subtask4)

    // Add to a task list if you are using one
    tasks.put(sampleTask.title, sampleTask)


    //loop for selection menu; top down approach needed to create from task to step
    loop@ while (true) {
        println(
            """
            |===========================
            |Select an option:
            |1. Create a new Task
            |2. Add Subtask to existing Task
            |3. Add Steps to Subtask
            |4. Display Task Checklist
            |5. Exit
            |===========================
        """.trimMargin()
        )

        when (readlnOrNull()?.trim()) {
            //create empty task
            "1" -> {
                print("Enter Task title: ")
                val title = readlnOrNull()?.trim().orEmpty()
                if (title.isNotEmpty()) {
                    tasks[title] = Task(title)
                    println("Task '$title' created.")
                }
            }

            //pick a task, then create subtask
            "2" -> {
                val task = chooseTask(tasks) ?: continue
                print("Enter Subtask title: ")
                val subtaskTitle = readlnOrNull()?.trim().orEmpty()
                if (subtaskTitle.isNotEmpty()) {
                    task.addSubtask(Subtask(subtaskTitle))
                    println("Subtask '$subtaskTitle' added to '${task.title}'.")
                }
            }

            //pick subtask then add step
            "3" -> {
                val task = chooseTask(tasks) ?: continue
                val subtask = chooseSubtask(task) ?: continue
                while (true) {
                    print("Enter instruction (or leave empty to stop): ")
                    val instruction = readlnOrNull()?.trim().orEmpty()
                    if (instruction.isEmpty()) break
                    print("Enter tool (optional): ")
                    val tool = readlnOrNull()?.trim().orEmpty()
                    print("Enter tip (optional): ")
                    val tip = readlnOrNull()?.trim().orEmpty()
                    subtask.addStep(Step(instruction, tool, tip))
                    println("Step added.")
                }
            }

            //display selected task
            "4" -> {
                val task = chooseTask(tasks) ?: continue
                println(task.generateChecklist())
            }

            //exit
            "5" -> break@loop

            else -> println("Invalid option. Try again.")
        }
    }

    println("Goodbye!")
}

fun chooseTask(tasks: Map<String, Task>): Task? {
    //check for tasks first
    if (tasks.isEmpty()) {
        println("No tasks available.")
        return null
    }

    println("Available Tasks:")
    tasks.keys.forEachIndexed { i, name -> println("${i + 1}. $name") }
    print("Select a task by number: ")
    val index = readlnOrNull()?.toIntOrNull()?.minus(1)
    return tasks.values.elementAtOrNull(index ?: -1).also {
        if (it == null) println("Invalid selection.")
    }
}

fun chooseSubtask(task: Task): Subtask? {
    val subtasks = task.getSubtasks()
    if (subtasks.isEmpty()) {
        println("No subtasks in '${task.title}'.")
        return null
    }

    println("Available Subtasks in '${task.title}':")
    subtasks.forEachIndexed { i, subtask -> println("${i + 1}. ${subtask.title}") }
    print("Select a subtask by number: ")
    val index = readlnOrNull()?.toIntOrNull()?.minus(1)
    return subtasks.getOrNull(index ?: -1).also {
        if (it == null) println("Invalid selection.")
    }
}
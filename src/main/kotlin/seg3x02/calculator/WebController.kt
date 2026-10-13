package seg3x02.calculator

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
class WebController {

    @ModelAttribute
    fun addAttributes(model: Model) {
        model.addAttribute("error", "")
        model.addAttribute("num1", "")
        model.addAttribute("num2", "")
        model.addAttribute("result", "")
    }

    @RequestMapping("/")
    fun home(): String {
        return "calculator"
    }

    @GetMapping(value = ["/calculate"])
    fun doCalculate(
        @RequestParam(value = "num1", required = false) num1: String,
        @RequestParam(value = "num2", required = false) num2: String,
        @RequestParam(value = "operation", required = false) operation: String,
        model: Model
    ): String {
        var n1: Double
        var n2: Double
        var res: Double = 0.0

        try {
            n1 = num1.toDouble()
            n2 = num2.toDouble()

            when (operation) {
                "add" -> res = n1 + n2
                "subtract" -> res = n1 - n2
                "multiply" -> res = n1 * n2
                "divide" -> {
                    if (n2 == 0.0) {
                        model.addAttribute("error", "DivisionByZero")
                        model.addAttribute("num1", num1)
                        model.addAttribute("num2", num2)
                        return "calculator"
                    }
                    res = n1 / n2
                }
                else -> {
                    model.addAttribute("error", "OperationError")
                    return "calculator"
                }
            }
            model.addAttribute("num1", num1)
            model.addAttribute("num2", num2)
            model.addAttribute("result", String.format("%.2f", res))
            
        } catch (exp: NumberFormatException) {
            model.addAttribute("error", "NumberFormatError")
            model.addAttribute("num1", num1)
            model.addAttribute("num2", num2)
        }
        
        return "calculator"
    }
}
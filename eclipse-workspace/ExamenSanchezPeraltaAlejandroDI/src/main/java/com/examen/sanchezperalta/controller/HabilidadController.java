package com.examen.sanchezperalta.controller;

import com.examen.sanchezperalta.entity.Habilidad;
import com.examen.sanchezperalta.service.HabilidadService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/habilidades")
public class HabilidadController {

    @Autowired
    private HabilidadService habilidadService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("habilidades", habilidadService.findAll());
        return "habilidad/list";
    }

    @GetMapping("/nueva")
    public String nuevaForm(Model model) {
        model.addAttribute("habilidad", new Habilidad());
        model.addAttribute("accion", "Nueva");
        return "habilidad/form";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute Habilidad habilidad,
                          BindingResult br, RedirectAttributes ra) {
        if (br.hasErrors()) return "habilidad/form";
        habilidadService.save(habilidad);
        ra.addFlashAttribute("msg", "Habilidad guardada correctamente");
        return "redirect:/habilidades";
    }

    @GetMapping("/editar/{id}")
    public String editarForm(@PathVariable Long id, Model model) {
        Habilidad h = habilidadService.findById(id).orElseThrow();
        model.addAttribute("habilidad", h);
        model.addAttribute("accion", "Editar");
        return "habilidad/form";
    }

    @PostMapping("/actualizar/{id}")
    public String actualizar(@PathVariable Long id,
                             @Valid @ModelAttribute Habilidad habilidad,
                             BindingResult br, RedirectAttributes ra) {
        if (br.hasErrors()) return "habilidad/form";
        habilidad.setId(id);
        habilidadService.save(habilidad);
        ra.addFlashAttribute("msg", "Habilidad actualizada correctamente");
        return "redirect:/habilidades";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes ra) {
        habilidadService.deleteById(id);
        ra.addFlashAttribute("msg", "Habilidad eliminada");
        return "redirect:/habilidades";
    }
}

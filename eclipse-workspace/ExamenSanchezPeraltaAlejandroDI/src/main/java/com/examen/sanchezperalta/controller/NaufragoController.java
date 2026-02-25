package com.examen.sanchezperalta.controller;

import com.examen.sanchezperalta.entity.Habilidad;
import com.examen.sanchezperalta.entity.Naufrago;
import com.examen.sanchezperalta.service.HabilidadService;
import com.examen.sanchezperalta.service.NaufragoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/naufragos")
public class NaufragoController {

    @Autowired private NaufragoService naufragoService;
    @Autowired private HabilidadService habilidadService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("naufragos", naufragoService.findAll());
        return "naufrago/list";
    }

    @GetMapping("/nuevo")
    public String nuevoForm(Model model) {
        model.addAttribute("naufrago", new Naufrago());
        model.addAttribute("todasHabilidades", habilidadService.findAll());
        model.addAttribute("accion", "Nuevo");
        return "naufrago/form";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute Naufrago naufrago,
                          BindingResult br,
                          @RequestParam(value = "habilidadesIds", required = false) List<Long> ids,
                          Model model, RedirectAttributes ra) {
        if (br.hasErrors()) {
            model.addAttribute("todasHabilidades", habilidadService.findAll());
            model.addAttribute("accion", "Nuevo");
            return "naufrago/form";
        }
        asignarHabilidades(naufrago, ids);
        naufragoService.save(naufrago);
        ra.addFlashAttribute("msg", "Náufrago guardado correctamente");
        return "redirect:/naufragos";
    }

    @GetMapping("/editar/{id}")
    public String editarForm(@PathVariable Long id, Model model) {
        Naufrago n = naufragoService.findById(id).orElseThrow();
        model.addAttribute("naufrago", n);
        model.addAttribute("todasHabilidades", habilidadService.findAll());
        model.addAttribute("accion", "Editar");
        return "naufrago/form";
    }

    @PostMapping("/actualizar/{id}")
    public String actualizar(@PathVariable Long id,
                             @Valid @ModelAttribute Naufrago naufrago,
                             BindingResult br,
                             @RequestParam(value = "habilidadesIds", required = false) List<Long> ids,
                             Model model, RedirectAttributes ra) {
        if (br.hasErrors()) {
            model.addAttribute("todasHabilidades", habilidadService.findAll());
            model.addAttribute("accion", "Editar");
            return "naufrago/form";
        }
        naufrago.setId(id);
        asignarHabilidades(naufrago, ids);
        naufragoService.save(naufrago);
        ra.addFlashAttribute("msg", "Náufrago actualizado correctamente");
        return "redirect:/naufragos";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes ra) {
        naufragoService.deleteById(id);
        ra.addFlashAttribute("msg", "Náufrago eliminado");
        return "redirect:/naufragos";
    }

    // Convierte la lista de IDs recibidos del formulario en entidades Habilidad
    private void asignarHabilidades(Naufrago naufrago, List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            naufrago.setHabilidades(new ArrayList<>());
            return;
        }
        List<Habilidad> habs = ids.stream()
            .map(id -> habilidadService.findById(id).orElse(null))
            .filter(Objects::nonNull)
            .toList();
        naufrago.setHabilidades(habs);
    }
}

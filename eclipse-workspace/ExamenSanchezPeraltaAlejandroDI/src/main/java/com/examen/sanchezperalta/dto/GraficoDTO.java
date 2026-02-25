package com.examen.sanchezperalta.dto;

public class GraficoDTO {
    private String label;
    private long count;

    public GraficoDTO(String label, long count) {
        this.label = label != null ? label : "Sin definir";
        this.count = count;
    }

    public String getLabel() { return label; }
    public long getCount()   { return count; }
}

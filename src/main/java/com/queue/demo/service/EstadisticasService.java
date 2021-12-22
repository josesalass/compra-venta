package com.queue.demo.service;

import com.queue.demo.model.ViewPromedioVentasMes;

import java.util.List;

public interface EstadisticasService {
    public List<ViewPromedioVentasMes> verPromedioVentas(int año);
}

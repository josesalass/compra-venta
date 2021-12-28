package com.queue.demo.service;

import com.queue.demo.model.ViewProductoMasVendidoPorMes;
import com.queue.demo.model.ViewPromedioVentasMes;
import com.queue.demo.model.ViewProductoMenosVendidoPorMes;

import java.util.List;

public interface EstadisticasService {
    public List<ViewPromedioVentasMes> verPromedioVentas(int año);
    public List<ViewProductoMenosVendidoPorMes> verProductoMenosVendido(int año);
    public List<ViewProductoMasVendidoPorMes> verProductoMasVendido(int año);
}

package com.queue.demo.service;

import com.queue.demo.model.ViewEgresosPorMes;
import com.queue.demo.model.ViewFlujoDeCaja;
import com.queue.demo.model.ViewIngresosPorMes;

import java.util.List;

public interface FlujoDeCajaService {

    List<ViewIngresosPorMes> verIngresosAnuales();

    List<ViewEgresosPorMes> verEgresosAnuales();

    List<ViewFlujoDeCaja> verFlujoDeCaja();
}

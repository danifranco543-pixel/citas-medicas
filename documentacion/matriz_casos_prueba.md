# Matriz de casos de prueba

**Proyecto:** Citas médicas  
**Técnicas:** partición de equivalencia y análisis de valores límite.

| ID | Escenario / datos | Tipo | Resultado esperado | Estado |
|---|---|---|---|---|
| CP-01 | Horario libre y 0 citas previas | Unitario | Se crea la reserva | Aprobado |
| CP-02 | 19 citas previas; se intenta la número 20 | Valor límite | Se acepta la reserva | Aprobado |
| CP-03 | 20 citas previas; se intenta la número 21 | Valor límite | Se lanza `CupoDiarioExcedidoException` y no se guarda | Aprobado |
| CP-04 | El profesional ya tiene una cita en la misma fecha y hora | Unitario | Se lanza `HorarioNoDisponibleException` y no se guarda | Aprobado |
| CP-05 | Profesional vacío | REST / validación | HTTP 400 | Aprobado |
| CP-06 | Fecha y hora pasada | REST / validación | HTTP 400 | Aprobado |
| CP-07 | Horario ocupado mediante API REST | MockMvc | HTTP 409 y cuerpo con `error` | Aprobado |
| CP-08 | Dos solicitudes con el mismo profesional y fecha/hora | Integración | Primera HTTP 201; segunda HTTP 409; queda una sola cita | Aprobado |

## Evidencia de ejecución

La ejecución de Maven reportó 13 pruebas: 0 fallos, 0 errores y 0 pruebas omitidas.

- `AgendaServiceTest`: 6 ejecuciones aprobadas.
- `CitaControllerTest`: 4 ejecuciones aprobadas.
- `CitaRepositoryTest`: 2 ejecuciones aprobadas.
- `ReservaIntegracionTest`: 1 ejecución aprobada.

**Conclusión:** todas las pruebas automatizadas ejecutadas finalizaron correctamente. Los estados de esta matriz se basan en los casos identificados en el código y en los informes de Maven.

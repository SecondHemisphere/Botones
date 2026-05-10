# Guía rápida del código fuente

## `activity_main.xml`

Define la interfaz visual usando `ScrollView` y `LinearLayout`. Incluye:

- `EditText` para escribir el nombre del estudiante.
- `Button` para mostrar un mensaje.
- `ToggleButton` para activar/desactivar el modo clase.
- Dos `ImageButton`: uno para sumar participaciones y otro para reiniciar el contador.
- `TextView` para mostrar el estado de la aplicación.

## `MainActivity.java`

La clase principal realiza cinco tareas:

1. Carga el layout con `setContentView`.
2. Busca los controles con `findViewById`.
3. Asigna eventos con `setOnClickListener`.
4. Guarda estado usando `SharedPreferences`.
5. Actualiza la interfaz con métodos pequeños y claros.

## Buenas prácticas importantes

- No se escriben textos directamente en el XML: se usan recursos en `strings.xml`.
- No se escriben colores directamente en el layout: se usan `colors.xml`.
- Los tamaños están en `dimens.xml`.
- Los `ImageButton` tienen `contentDescription` para accesibilidad.
- Se evitan dependencias externas para reducir errores de Gradle.

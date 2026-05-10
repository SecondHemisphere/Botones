# Botones Buenas Prácticas - Android Java + XML

Ejemplo académico para el tema **1.5.4 Botones**.

## Compatibilidad

- Gradle Wrapper: 8.13
- Android Gradle Plugin: 8.13.2
- JDK: 17
- compileSdk: 35
- minSdk: 23
- Lenguaje: Java
- Interfaz: XML
- Dependencias externas: ninguna

## Qué demuestra

1. `Button` para ejecutar una acción textual.
2. `ToggleButton` para manejar estado binario ON/OFF.
3. `ImageButton` para acciones visuales con íconos.
4. Eventos con `setOnClickListener`.
5. Persistencia simple con `SharedPreferences`.
6. Buenas prácticas: `strings.xml`, `colors.xml`, `dimens.xml`, `contentDescription`, separación de responsabilidades y código Java ordenado.

## Cómo abrir

1. Descomprime el ZIP.
2. Abre Android Studio.
3. Selecciona **Open** y elige la carpeta `BotonesBuenasPracticas_Gradle813`.
4. Verifica que el **Gradle JDK** sea `Embedded JDK 17`.
5. Sincroniza el proyecto y ejecuta la app.

## Sugerencia si Android Studio muestra errores de caché

Cierra Android Studio y ejecuta dentro de la carpeta del proyecto:

```bash
rm -rf .gradle .idea
```

Luego abre nuevamente el proyecto.

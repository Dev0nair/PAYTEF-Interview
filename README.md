# PAYTEF - Prueba técnica

## Resumen técnico
Uso de:
- Kotlin + Jetpack Compose + Clean Architecture + MVVM
- Hilt - Dependency Injection
- Coroutines
- Flows + StateFlow
- Testing: JUnit4, Mockito, Turbine y CoroutineTesting
- Dark Theme & Light Theme

## Organizacíon
Este proyecto consta de 2 módulos:
- App: Incorpora los elementos propios de Android y lo básico para la aplicación que estamos construyendo: la configuración de hilt, las pantallas a usar, la configuración del tema...
- Core: Incorpora la construcción de los elementos que compondrán la aplicación: las pantallas, componente, casos de uso, repositorios... Además, estará dividido por sus capas de clean architecture respetando la visibilidad y uso entre capas
  - Presentation: Contendrá todo lo relacionado con la vista y la implementación del intermediario de la vista con la lódica de negocio (los viewModels)
    - Screens: Tendrá la construcción de las pantallas de la app por individual. Son las que usaremos para la navegación
    - Components: Tendrá los elementos individuales que se verán en cada pantalla
    - Models: Tendrá los modelos a usar para la visualización de elementos en la vista. Esto no tendrá que ver con las entidades de dominio.
    - Utils: Tendrá las utilidades necesarias para facilitar alguna que otra lógica como la de convertir un texto en minúscula con solo la primera letra en mayúscula
  - Dominio: Contendrá la lógica de negocio de la aplicación
    - Entity: Las entidades que conformarán los datos con los que se va a trabajar
    - Repository: Contendrá los contratos (interfaces) que necesitaremos para la utilización y manejo de datos a la hora de seguir la lógica de negocio
    - UseCase: Los casos de uso son las unidades lógicas de negocio. Determinará las funciones a procesar concretas para una utilidad
    - Utils: Contiene las utilidades para facilitar algunos desarrollos, como el formateo de fechas para almacenar en base de datos
    - Di: Aquí se configuran las inyecciones a realizar, para poder proporcionar las necesidades de los elementos de dominio allá donde sea necesario.
  - Data: Aquí se tratará toda la gestión de los datos a usar por la lógica de negocio (almacenar, consultar, actualizar...)
    - DataSource: Como bien dice su nombre, aquí se establecerían las fuentes de datos. Estas pueden ser locales por variables, fuentes de datos por API, firebase, room... para satisfacer esos contratos que serán necesarios en la capa de dominio
    - Di: Aquí se proporcionarán las instancias a inyectar allá donde sea necesario (como la implementación de los repos para ser recogido por dominio)
    - Repository: Aquí se realizarán las implementaciones de los contratos de repositorios creados en dominio, para hacer uso del / de los DataSource

## Qué se aborda en esta prueba
### Pantallas
- Dashboard: Tendrá una Toolbar y una LazyGridList para mostrar un listado en cuadrados dentro de 2 columnas, creciendo verticalmente. Aquí se gestionará las entradas principales de navegación dentro de la aplicación. Actualmente, solo se preparó para navegar a la pantalla de Casos
- Cases: Aquí se mostrará una Toolbar con 2 botones y el listado de casos a los que el usuario actual tendrá acceso (lectura o total).
  - Los botones de la toolbar serán:
    - Lado izquierdo: Botón de ir hacia atrás
    - Lado derecho: Botón para crear un caso de prueba asociado como creador al usuario actual (para poder mostrar datos)
- LoadingScreen: Esta pantalla se mostrará mientras se estén cargando los datos. En este caso al ser datos locales, difícilmente será perceptible.
- ErrorScreen: Esta pantalla se mostrará cada vez que haya algun error, permitiendo al usuario volver a navegar hacia atrás o volver al dashboard.

### Lógica implementada
Se implementa los casos de uso necesarios para satisfacer las necesidades descritas en el correo con los detalles de esta prueba, aunque a efectos prácticos, el usuario solo podrá:
- Acceder inicialmente al Dashboard
- Navegar a la pantalla de Cases
- Crear un Caso de prueba nuevo
- Ver el listado de casos a los que tendrá acceso
- Implementar el excel aportado en el correo para tener un listado de ejemplo para mostrar en la pantalla Cases

Lo demás, como se indica en la descripción de la prueba, será probado como test cases dentro de la capa de dominio (donde se encuentra los casos de uso). Fichero: BatteryTest
PD: Al igual que se puede testear los flows del caso de uso, se puede testear de la misma manera los StateFlows de los viewModel.

Casos a testear: 
[x] Conocer los casos a los que puede acceder un abogado con su permiso asociado
[x] Conocer los abogados que tienen acceso a un caso y el tipo de permiso que tienen
[x] Asignar y eliminar un permiso de solo lectura para un caso específico
[x] Asignar y eliminar un permiso de acceso total para un caso específico
[x] Asignar un permiso global solo lectura a otro abogado para todos los casos de un abogado
[x] Asignar un permiso global de acceso total pero bajar un caso para que sea solo lectura

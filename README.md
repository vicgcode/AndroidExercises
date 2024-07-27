- [Android](#android)
  - [Activity](#activity)

# Android

## Activity
[Yandex - базовые компоненты: activity](https://youtu.be/B6LUhon7baU?list=PLQC2_0cDcSKAVl_3u-3ZrEW2UFBUjDD97&t=126)

Активити - это компонент (класс), предоставляющий окно для отрисовки интерфейса для взаимодействия пользователя с программой. 
Жизненный цикл - это набор состояний, в которых activity может находиться.
1. Created (stopped) - activity создано и подготовлено для отрисовки, но пользователь его еще не видит.
2. Visible (paused) - видно пользователю, но взаимодействие недоступно. Обычно это состояние быстро проходит, но мы можем находиться в нем дольше, если, например, поверх нашего activity 
отображается что-либо: диалоговое окно (activity частично закрыта). В режиме мультиоконности только одно из приложений находится в фокусе, а второе в этот момент находится в состоянии
visible.
3. Resumed (active) - activity видно и доступно для взаимодействия.
На эти три состояния достаточно легко наложить методы (колбэки) жизненного цикла.
![image](https://github.com/user-attachments/assets/884d4968-20c4-4617-8cb9-cf25db8745bd)
- onCreate() - вызывается 1 раз за весь ЖЦ. В нем мы можем настроить адаптеры, проинициализировать view и т.д.
- onDestroy() - может не вызваться в следующих ситуациях: когда activity уже не видна пользователю (в состоянии stopped) и системе может потребоваться дополнительная память,
тогда система уничтожит activity без вывоза onDestroy. Какие-то задачи следует завершить до onDestroy.


Activity может менять свое состояние при изменении конфигурации.
- Разворот экрана
- Изменение языка
- Подключение внешней клавиатуры
В этот момент у activity вызываются методы onPause(), onStop(), onDestroy(). Создается новый инстанс activity.
![image](https://github.com/user-attachments/assets/6c5121f6-0ba2-4d25-89d8-6398c2b3acf2)
Почему так сделано - когда конфигурация меняется, наш экран должен к ней адаптироваться - получить ресырсы из другого языка, перерисовать views и так далее.
При смене конфигурации важно сохранить состояние, в котором находился пользователь - сохранить введенные на экране данные, например. Google предлагает три варианта сохранения
данных:
- Storage - сохранение данных в базе данных, в каком-то хранилище.
- ViewModel - этот архитектурный компонент переживает пересоздание activity, данные можно сохранить в нем.
- onSaveInstanceState().

onSaveInstanceState(outstate: Bundle) - метод, который вызывается при пересоздании actvivity, и мы можем его переопределить. На вход он принимает аргумент Bundle - по сути
контейнер, куда можно сложить данные. Bundle хранит данные в виде пар ключ-значение. Значения могут быть примитивами, а также Parcelable и Serializable.

Parcelable и Serializable - два интерфейса сериализации.
* Serializable - пришел из джавы. Объект сериализуется, используя рефлексию. Помечаем класс @Serializable. Рефлексия требует определенного времени и ресурсов. (!!! подробнее)
* Parcelable - создан специально для android. Отличие в том, что когда мы используем интерфейс Parcelable мы должны самостоятельно описать методы записи и чтения parcelable,
в которых описываем, как именно нужно сохранять данные. Можно не описывать эти методы руками, а пометить класс аннотацией @Parcelize, тогда эти методы сами сгенерируются в момент
компиляции (они достаточно шаблонные).

Сохраненное состояние нужно восстановить - для этого есть два методы: onCreate(savedInstanceState: Bundle?) и onRestoreInstanceState(savedInstanceState: Bundle). В onCreate
Bundle? приходит nullable, так как если activity создается с нуля, то никакого состояния не будет. (!!! в чем еще отличие)

Когда SavedInstanceState не поможет, если:
- onBackPressed() пользователь нажал "назад"
- crash
- вызвали finish()
- удаление из списка запущенных приложений вручную
Можно использовать более долгосрочные способы хранения данных.


P.S. Некоторые view сами сохраняют состояние (у view тоже есть метод onSaveInstanceState()). Необходимо указывать id у view:
- EditText
-  RecyclerView


P.P.S. Можно не пересоздавать activity при смене конфигурации. Для этого в Манифесте у activity необходимо указать, какие configChanges мы хотим обрабатывать самостоятельно.
Вместо пересоздания у activity вызывается метод onConfigurationChanged(newConfig: Configuration) с аргументом Configuration, из которого мы можем прочитать новую кофигурацию.
![image](https://github.com/user-attachments/assets/9fe3a6dd-6dd4-44f9-869a-ab813134bc7f)



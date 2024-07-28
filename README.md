- [Android](#android)
  - [Activity Lifecycle](#activity)
  - [Activity Creating](#activity_creating)
  - [View Lifecycle](#view)
  - [Fragment Lifecycle](#fragments)
  - [RecyclerView](#recyclerview)

# Android

## Activity
[Yandex - базовые компоненты: activity](https://youtu.be/B6LUhon7baU?list=PLQC2_0cDcSKAVl_3u-3ZrEW2UFBUjDD97&t=126)
[Android Academy - Activity Lifecycle](https://youtu.be/Gb71h-cEUZs?t=606)

Активити - это компонент (класс), предоставляющий окно для отрисовки интерфейса для взаимодействия пользователя с программой. 
Жизненный цикл - это набор состояний, в которых activity может находиться.
1. Created (stopped) - activity создано и подготовлено для отрисовки, но пользователь его еще не видит.
2. Visible (paused) - видно пользователю, но взаимодействие недоступно. Обычно это состояние быстро проходит, но мы можем находиться в нем дольше, если, например, поверх нашего activity 
отображается что-либо: диалоговое окно (activity частично закрыта). В режиме мультиоконности только одно из приложений находится в фокусе, а второе в этот момент находится в состоянии
visible.
3. Resumed (active) - activity видно и доступно для взаимодействия.
На эти три состояния достаточно легко наложить методы (колбэки) жизненного цикла.
![image](https://github.com/user-attachments/assets/884d4968-20c4-4617-8cb9-cf25db8745bd)
- onCreate (created) - вызывается 1 раз за весь ЖЦ. В нем мы можем настроить адаптеры, проинициализировать view и т.д. В этот момент просчитываются размеры, положение компонентов.
- onRestoreInstanceState - вызывается перед onStart.
- onStart (visible) - макет готов, мы показываем его пользователю, входит в состояние Visible.
- onResume (resumed) - экран доступен к взаимодействию, входит в состояние Resumed.
- onPause (visible) - первый колбэк при уничтожении активити. Вызывается при частичном закрытии activity. Входит в состояние Visible.
- onStop (created) - после вызова этого метода пользователь уже не видит экран. Входит в состояние Created (stopped). После onStop() возможно два варианта:
  - onRestart (visible) - активити вновь становится видимой. Например, мы вернулись назад к предыдущий активити.
  - onDestroy() - уничтожение активити.
- onSaveInstanceState() - вызывается перед onDestroy.
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

Parcelable и Serializable - два интерфейса сериализации. Сериализуют объект в массив байтов, чтобы передать по сети или между экранами, и дересериализуют обратно.
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


## Activity_creating

[Android Academy - Activity Creating](https://youtu.be/Gb71h-cEUZs?t=739)

Как запустить новую активити? Launch Other Activity:
```startActivity(Intent(context, MyActivity::class.java))```
Это открытие активити через явный интент. Кроме того, мы можем использовать неявные интенты для открытия активити с необходимым функционалом.
```val intent = Intent("some.intent")```
```startActivity(intent)```
Необходимо, чтобы в файле Манифеста запрашиваемая активити имела в своих интент-фильтрах указанный интент. Например:
```<activity android:name="IntentActivity">```
```<intent-filter>```
```<action android:name="some.intent" />```

### Activity Launch Mode
#### Как объявить launch mode?
1. В манифесте:
   ```<activity```
   ```android:name=".SingleTaskActivity"```
   ```android:launchMode="singleTop">```
2. Помимо назначения режима запуска непосредственно в AndroidManifest.xml,
мы также можем регулировать поведение с помощью инструмента, называемого Intent-флагами, например 
```val intent = Intent(context, MyActivity::class.java)```
```intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)```
```startActivity(intent)```

#### Словарь
- Launch mode - режим запуска активити и ее положение в бэкстэке.
- Backstack - стэк переходов назад.
![image](https://github.com/user-attachments/assets/bca4f9eb-fdf7-426c-ac41-78150f902095)
- Таск - набор активити, с которыми работает пользователь в рамках одной задачи. Активити упорядочены в таске в виде бэкстэка. В фоновом режиме может быть несколько тасков.


#### Режимы запуска активити:
1. Standart.
На каждый вызов startActivity() он добавляет новую активити в стэк, независимо от того, есть ли у нас такая активити в стэке. Таким образом, в стэке будет несколько
активити одного типа. Пользователь может закрыть все активити с помощью onBackPressed() и он попадет на главный экран своего смартфона.
2. SingleTop. 
Отличие от standart в том, если в топе стэка уже есть активити, которую пользователь пытается заново открыть, то она не будет создана, 
а в уже существующей активити вызовется метод onNewIntent().
![image](https://github.com/user-attachments/assets/e050ea4a-3527-4ba3-9b69-6eb9de639f88)
3. SingleTask.
Activity с singleTask launchMode разрешено иметь только один экземпляр в системе (аля синглтон)
В этом режиме не имеет значение, в топе стэка активити или просто лежит где-то в стэке. Допустим, у нас есть по порядку открытые активити: А, Б, С. Если мы попытаемся
открыть активити A из С, то все активити, расположенные в стэке над A, будут уничтожены, а в активити А вызовется onNewIntent().
4. SingleInstance.
На каждый вызов открывает активити в новой таске с собственным бэкстэком. 
Этот режим очень похож на singleTask, где в системе мог существовать только один экземпляр Activity. Разница в том, что задача, которая располагает этим Activity, 
может иметь только одно Activity — то, у которого атрибут singleInstance.
![image](https://github.com/user-attachments/assets/05a9a498-f052-4fdf-8d3a-8335ac812b77)
onBackPressed работает исключительно по текущей у пользователя таске.


## View

[Yandex - View LifeCycle](https://youtu.be/7Xg1HSox8QI?list=PLXtiZNKIobF5E1JgDaisqnVJfbZeUFYkm&t=2596)
[Yandex - View](https://www.youtube.com/watch?v=YEcVkUN6caw)

В тот момент, когда view аттачится к activity, передается в setContentView() в onCreate(), у нее запускается собственный жизненный цикл со своими колбэками.
![image](https://github.com/user-attachments/assets/7fb9443b-da92-40be-9a2d-779a23ca0895)

Этапы:
1. Measure pass: расчет размеров. Перед тем, как отрисовать view на экране, нам нужно определить ее размеры. Может происходить дважды, например, если у LinearLayout 
заданы weight. Для нестандартного поведения переопределяем onMeausure().
- Проходим по дереву контейнера сверху вниз
- родитель передает детям требования к размерам
- view рассчитывает свои размеры, сохраняет их во внутренний стейт measuredWidth и measuredHeight
2. Layout pass: view позиционируются внутри ViewGroup. Для нестандартного поведения переопределяем onLayout().
- Проходим по дереву контейнера сверху вниз
- Позиционируем view в контейнере
3. Drawing: отрисовка.
  - View может быть перерисована, для этого существуют методы invalidate() и requestLayout().
  - ```requestLayout()``` перезапускает весь жизненный цикл (вызов onDraw не гарантирован). 
  - ```invalidate()``` размеры и положение view не меняются, но методы отрисовки drawing вызываются заново (изменился цвет background, например).
![image](https://github.com/user-attachments/assets/38a41766-c9d1-4ca2-b76c-f4a26e352ed1)
Кроме того, у View есть методы сохранения состояния.
- ```onSaveInstanceState(): Parcelable``` - ```return bundle```
- ```onRestoreInstanceState(p: Parcelable)``` - ```p as Bundle.getParcelable()```


## Fragments


## RecyclerView

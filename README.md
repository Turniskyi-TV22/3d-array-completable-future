Короткий опис:

Створюється потокобезпечний CopyOnWriteArrayList для збереження матриці 3х3 - matrix;

Створюється generateTask та ініціалізується з допомогою метода runAsync, що генерує CompletableFuture, який генерує та виводить матрицю до консолі;

Створюється массив обєктів CompletableFuture під назвою readTasks;

Далі в циклі для кожного стовпчика з допомогою supplyAsync додаються CompletableFuture до readTasks, що повертають цей стовпчик.

Після викликається thenAcceptAsync, що отримує стовпчик та виводить його в консоль.

CompletableFuture.allOf(readTasks).join() - чекає завершення readTasks, після чого програма закінчується. 

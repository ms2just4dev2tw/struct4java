package org.self.queue;

/**
 * 环形队列
 * <p>
 * 避免一般顺序存储队列的假溢出问题
 * <p>
 * 每次进队循环队尾索引：{@code rear = (rear+1)%length} <p>
 * 每次出队循环队首索引：{@code front = (front+1)%length}
 * <p>
 * 队列为空：{@code rear == front} <p>
 * 队列满队：{@code (rear+1)%length == front}，这里队列中还有一个空位，但为了不与队空混淆
 * <p>
 * 
 * @author TungWang
 *
 * @param <E>
 */
public class CircularQueue<E> implements Queue<E> {

	// 队首
	protected int front;
	// 队尾
	protected int rear;
	// 数组的实际容量
	protected int capacity;
	protected Object data[];
	// 默认初始化的容量
	private final static int DEFAULT_CAPACITY = 16;

	public CircularQueue() {
		this(DEFAULT_CAPACITY);
	}

	/**
	 * 由于环形队列的判断队空和队满的机制，data的实际容量会比 capacity 多 1
	 * 
	 * @param initCapacity 初始化的容量大小
	 */
	public CircularQueue(int initCapacity) {
		if (initCapacity <= 0)
			throw new IllegalArgumentException("the initCapacity less than or equal to 0");
		capacity = initCapacity;
		data = new Object[capacity + 1];
	}

	@Override
	public boolean enter(E e) {
		if (isfull())
			resize();
		data[rear] = e;
		rear = (rear + 1) % capacity;
		return true;
	}

	private boolean isfull() {
		return (rear + 1) % capacity == front;
	}

	private void resize() {
		validateOutOfMemory();
		capacity = capacity << 1;
		Object newData[] = new Object[capacity + 1];
		// 将旧的数据拷贝到新开辟的数组
		for (int index = 0; index < data.length; index++)
			newData[index] = data[index];
		// 数组索引指向新开辟的数组
		data = newData;
	}

	/**
	 * 由于扩容机制是左移运算，所以 {@code Integer.MAX_VALUE - (Integer.MAX_VALUE >> 1)}
	 * 的结果{@code 01000000000000000000000000000000 1073741824}
	 * <p>
	 * 如果容量大于等于这个限值，就不能再扩容了
	 */
	private void validateOutOfMemory() {
		if (capacity >= 1073741824)
			throw new OutOfMemoryError("capacity greater than 1073741824,");
	}

	@Override
	public E poll() {
		if (!isEmpty()) {
			@SuppressWarnings("unchecked")
			E obj = (E) data[front];
			front = (front + 1) % capacity;
			return obj;
		} else
			throw new IllegalCallerException("the queue is empty, you can not caller");
	}

	@SuppressWarnings("unchecked")
	@Override
	public E peek() {
		if (!isEmpty())
			return (E) data[front];
		else
			throw new IllegalCallerException("the queue is empty, you can not caller");
	}

	@Override
	public boolean isEmpty() {
		return front == rear;
	}

}

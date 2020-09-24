package correcter;

public class List<N extends Comparable<N>> {

    private Node<N> head = null;
    private Node<N> tail = null;

    public void addNode(N value) {
        Node<N> newNode = new Node(value);

        addNode(newNode);
    }

    public void addNode(N value, boolean isMarked) {
        Node<N> newNode = new Node(value, isMarked);

        addNode(newNode);
    }

    private void addNode(Node<N> newNode) {
        if (head == null) {
            head = newNode;
        } else {
            tail.nextNode = newNode;
            newNode.prevNode = tail;
        }

        tail = newNode;
        tail.nextNode = head;
    }

    public boolean containsNode(N searchValue) {

        Node<N> currentNode = head;

        if (head == null) {
            return false;
        } else {
            do {
                if (currentNode.value == searchValue) {
                    return true;
                }
                currentNode = currentNode.nextNode;
            } while (currentNode != head);
            return false;
        }
    }

    public Node<N> getMarkedNode() {
        Node<N> currentNode = head;

        if (head != null) {
            do {
                if (currentNode.isMarked) {
                    return currentNode;
                }

                currentNode = currentNode.nextNode;
            } while (currentNode != head);
        }

        return null;
    }

    public void deleteNode(N valueToDelete) {

        Node<N> currentNode = head;

        if (head != null) {
            if (currentNode.value == valueToDelete) {
                head = head.nextNode;
                tail.nextNode = head;
            } else {
                do {
                    Node<N> nextNode = currentNode.nextNode;
                    if (nextNode.value == valueToDelete) {
                        currentNode.nextNode = nextNode.nextNode;
                        break;
                    }
                    currentNode = currentNode.nextNode;
                } while (currentNode != head);
            }
        }
    }

    public void traverseList() {

        Node<N> currentNode = head;

        if (head != null) {
            do {
                currentNode = currentNode.nextNode;
            } while (currentNode != head);
        }
    }

}

class Node<N extends Comparable<N>> {
    public final N value;
    public boolean isMarked;
    public Node<N> nextNode;
    public Node<N> prevNode;

    public Node(N value) {
        this.value = value;
    }

    public Node(N value, boolean isMarked) {
        this.value = value;
        this.isMarked = isMarked;
    }
}
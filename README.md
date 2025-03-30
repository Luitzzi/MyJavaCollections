![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)<br/>

![Searchtree UML](/images/mycollections.jpg)<br/>
Welcome to MyJavaCollection - A implementation of parts of the Java Collections Framework

---

Throughout the course "Algorithms and Datastructures" I implemented multiple algorithms dealing with collections.
This project is a summary of them organised in the structure of the java collections framework.

## Algorithms
* **MyTreeSet**<br/>
  * **SearchTree**<br/>
  * **AvlTree**<br/>
  * **SkipList**<br/>

* **MyHashSet**<br/>

## What did I learn?
* **First of all how the algorithms work, as well as their pros and cons**<br/><br/>
* **Implement generic classes following an OOP-Structure**<br/>
  * All algorithms are implemented in a generic way allowing all datatypes extending Comparable. <br/>
  * The searchtree algorithms follow a hierarchical structure:
    * AbstractSearchtree implements the core functionality of a searchtree and defines the node generically.
      With this the searchtree variations overwrite the necessary methods and can define the specific node they need.<br/>
    * The following UML-Diagram displays this structure:<br/>
  ![Searchtree UML-Diagram](/images/trees.png)
* **Test the functionality of the classes using the framework JUnit**<br/>
  * To ensure that the algorithms work properly and to cover all edge cases I tried out the testing framework JUnit.
  
## File Strucutre
```
.
├── MySet
│   ├── MyTreeSet
│   │   ├── AvlTree
│   │   │   ├── AvlSearchtree
│   │   │   ├── AvlTreeNode
│   │   ├── SearchTree
│   │   │   ├── BasicTreeNode
│   │   │   ├── Searchtree
│   │   ├── SkipList
│   │   │   ├── Skiplist
│   │   │   ├── SkipListNode
│   │   ├── AbstractSearchTree
│   │   ├── Tree
│   │   ├── TreeFactory
│   │   ├── TreeNode
│   ├── MyHashSet
│   │   ├── MyHashSet
│
├── test
│   ├── MyHashSetTest
│   │   ├── MyHashSetTest
│   ├── MyTreeSetTest
│   │   ├── AvlSearchtreeTestDeletion
│   │   ├── AvlSearchtreeTestInsertion
│   │   ├── PrintTreeTest
│   │   ├── SearchtreeTest
│   │   ├── SkiplistTest
```

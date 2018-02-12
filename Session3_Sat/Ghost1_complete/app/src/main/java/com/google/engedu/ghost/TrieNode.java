/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.engedu.ghost;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;


public class TrieNode {
    private HashMap<String, TrieNode> children;
    private boolean isWord;
    //private Random random = new Random();

    public TrieNode() {
        children = new HashMap<>();
        isWord = false;
    }

    /**
     * Add the new word in the Trie by appending at the appropriate
     * existing node or create a new node and than insert it
     * @param s
     */
    public void add(String s) {
   
    }

    /**
     * Search for the word starting with the input prefix
     * @param s
     * @return returns appropriate object of the TrieNode or null
     */
    private TrieNode searchNode(String s){
       
    }

    public boolean isWord(String s) {
        
    }

    /**
     * Computer plays simply by selecting the same word every time
     * obtained from the Binary Search
     * @param s
     * @return returns either the selected word or an appropriate string
     */
    public String getAnyWordStartingWith(String s) {
        
    }

    /**
     * Computer plays smartly by selecting a random word from the
     * short listed possible words.
     * @param s
     * @return returns either the selected word or an appropriate string
     */
    public String getGoodWordStartingWith(String s) {
        
     
}

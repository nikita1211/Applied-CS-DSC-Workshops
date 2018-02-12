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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

public class SimpleDictionary implements GhostDictionary {
    private ArrayList<String> words;

    public SimpleDictionary(InputStream wordListStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(wordListStream));
        words = new ArrayList<>();
        String line = null;
        while((line = in.readLine()) != null) {
            String word = line.trim();
            if (word.length() >= MIN_WORD_LENGTH)
              words.add(line.trim());
        }
    }

    @Override
    public boolean isWord(String word) {
        return words.contains(word);
    }

    /**
     * Computer plays simply by selecting the same word every time
     * obtained from the Binary Search
     * @param prefix
     * @return returns either the selected word or an appropriate string
     */
    @Override
    public String getAnyWordStartingWith(String prefix) {

        /**
         * If computer's turn is first, prefix would be null,
         * so we append the first character from the random selected word
         */
        if(prefix == ""){
            Random random = new Random();
            int randomIndex = random.nextInt(words.size());
            String randomPrefixWord = words.get(randomIndex);
            String randomPrefix = String.valueOf(randomPrefixWord.charAt(0));
            return randomPrefix;
        }
        /**
         * If computer's turn is not first, prefix would not be null,
         * so we search for the word and return appropriate string
         */
        else{
            int indexOfWordForComputer = searchIfWordIsPossible(prefix);
            String wordForComputer;
            if(indexOfWordForComputer != -1){
                wordForComputer = words.get(indexOfWordForComputer);
                if(wordForComputer.equals(prefix))
                    return "sameAsPrefix";
                else
                    return wordForComputer;
            }
            else
                return "noWord";
        }
    }

    /**
     * Search for the word starting with the input prefix
     * @param prefix
     * @return either index of the found word or -1
     */
    public int searchIfWordIsPossible(String prefix){
        int lowerIndex = 0;
        int upperIndex = words.size() - 1;
        int middleIndex, mismatchValue;
        String wordFromWordsList;
        while(lowerIndex <= upperIndex){
            middleIndex = (lowerIndex + upperIndex)/2;
            wordFromWordsList = words.get(middleIndex);
            mismatchValue = wordFromWordsList.startsWith(prefix) ? 0 : prefix.compareTo(wordFromWordsList);
            if(mismatchValue == 0)
                return middleIndex;
            else if(mismatchValue > 0)
                lowerIndex = middleIndex + 1;
            else
                upperIndex = middleIndex - 1;
        }
        return -1;
    }

    /**
     * Computer plays smartly by selecting a random word from the
     * short listed possible words. Think of downIndex as the index
     * moving from right to left & upIndex as the index moving from
     * left to right from the possibleWordIndex and all the indices
     * from a horizontal view
     * @param prefix
     * @param whoEndsFirst is 1 if it's user's turn, else 0
     * @return returns either the selected word or an appropriate string
     */
    @Override
    public String getGoodWordStartingWith(String prefix, int whoEndsFirst) {
        String selected = null;
        int  possibleWordIndex,upIndex,downIndex,t;
        String possibleWord,wordFromWordsList;
        ArrayList<String> oddWordSet = new ArrayList<>();
        ArrayList<String> evenWordSet = new ArrayList<>();
        int rangeBegin=-1,rangeEnd=-1;//all words starting with prefix
        int begin = 0, end = words.size()-1,mid;
/*
        ArrayList<String> shortListedWord = new ArrayList<String>();
        Random randomWordIndex = new Random();

        if(prefix == ""){
            Random random = new Random();
            int randomIndex = random.nextInt(words.size());
            return words.get(randomIndex);
        }
        else{
            possibleWordIndex = searchIfWordIsPossible(prefix);
            upIndex = downIndex = possibleWordIndex;
            if(possibleWordIndex == -1){
                return "noWord";
            }
            possibleWord = words.get(possibleWordIndex);
            shortListedWord.add(possibleWord);
            while(true){
                upIndex++;
                if(upIndex == words.size()){
                    break;
                }
                wordFromWordsList = words.get(upIndex);
                t = wordFromWordsList.startsWith(prefix)? 0 : prefix.compareTo(wordFromWordsList);
                if(t != 0){
                    break;
                }
                if(wordFromWordsList.length()%2 == whoEndsFirst){
                    shortListedWord.add(wordFromWordsList);
                }
            }
            while(true){
                downIndex--;
                if(downIndex < 0){
                    break;
                }
                wordFromWordsList = words.get(downIndex);
                t = wordFromWordsList.startsWith(prefix)? 0 : prefix.compareTo(wordFromWordsList);
                if(t != 0){
                    break;
                }
                if(wordFromWordsList.length()%2 == whoEndsFirst){
                    shortListedWord.add(wordFromWordsList);
                }
            }
        }
        if(shortListedWord.size() == 0){
            return "noWord";
        }else{
            int selectedWordIndex = randomWordIndex.nextInt(shortListedWord.size());
            selected = shortListedWord.get(selectedWordIndex);
            if(selected.equals(prefix)){
                return "sameAsPrefix";
            }
        }*/
        int begin = 0, end = words.size()-1,mid;
        while (begin<=end)
        {
            Log.d("Begin"+begin,"End"+end);
            mid=(begin+end)/2;
            Log.d("Mid"+mid,"Word"+words.get(mid));
            if(words.get(mid).startsWith(prefix))
            {
                Log.d("TRue","Prefix"+prefix);
                for(int i=mid-1;i>=0;i--)
                {
                    if(!words.get(i).startsWith(prefix))
                    {
                        rangeBegin=i+1;
                        break;
                    }
                }
                for(int i=mid+1;i<words.size();i++)
                {
                    if(!words.get(i).startsWith(prefix))
                    {
                        rangeEnd=i-1;
                        break;
                    }
                }
                break;
            }
            else
            {
                if(words.get(mid).compareTo(prefix)>0)
                {
                    end = mid-1;
                }
                else
                {
                    begin = mid +1;
                }
            }
        }
        if(rangeBegin == -1 || rangeEnd == -1)
            return selected;//null;
        for(int i=rangeBegin;i<rangeEnd;i++)
        {
            if(words.get(i).length()%2==0)
                evenWordSet.add(words.get(i));
            else
                oddWordSet.add(words.get(i));
        }
        Log.d("even",""+evenWordSet);
        Log.d("odd",""+oddWordSet);
        if(prefix.length()%2==0)//odd prefix odd word
        {
            if(!oddWordSet..get(random.nextInt(oddWordSet.size())); //Random word better than largest word to avoid repeated words
            else {
                if (!evenWordSet.isEmpty())
                    selected = evenWordSet.get(random.nextInt(evenWordSet.size())); //when computer knows that it has to loose
                //else no words
            }
        }
        else//even prefix even word
        {
            if(!evenWordSet.isEmpty())
                selected=evenWordSet.get(random.nextInt(evenWordSet.size()));
            else{
                if (!oddWordSet.isEmpty())
                selected=oddWordSet.get(random.nextInt(oddWordSet.size()));
            }
        }
        return selected;
    }
}

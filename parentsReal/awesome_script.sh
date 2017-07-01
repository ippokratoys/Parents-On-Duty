i=0
for file in *
do
    ((i++))
    if [[ $file != *".sh"* ]];
    then
        extension="${file##*.}"
        mv "$file" "$i"."$extension"
    fi
done

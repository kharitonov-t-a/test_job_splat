export default interface GenericList<T> {

    itemList : Array<T>;

    editItem(item: T) : void

    cleanForm() : void

    deleteItem(item : T) : void

    activateItem(item : T) : void

    switchActivity(item : T, newActivityState : boolean) : void

    saveItem(item : T) : void

    showHistory(item : T) : void

}

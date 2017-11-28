void process_CAN_msg(CAN_message msg){
  uint16_t * lsbArray = (uint16_t *) msg.data;
  switch (msg.id) {
    {% loop in $messages %}
    //{$comment}
    case {$id}:
{$fields}
    break;

    {% onEmpty %}
    {% endloop %}
  }
}


      {% loop in $fields %}
      //{$comment}
      CAN_extract_numeric(data, {$pos}, {$len}, {$endian}, {$sgn}, {$scl}, {$off});
      {% onEmpty %}
      {% endloop %}